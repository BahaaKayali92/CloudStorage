package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public String viewHome(Authentication authentication, Model model, Note note, Credential credential) {
        User user = userService.getUser(authentication.getName());
        Integer userid = user.getUserid();
        File[] files = fileService.getFiles(userid);
        Note[] notes = noteService.getNotesByUserId(userid);
        Credential[] credentials = credentialService.getCredentialsByUserId(userid);
        Map<Integer, String> decryptedPasswords = new HashMap<Integer, String>();
        if (credentials.length > 0) {
            for (Credential cred: credentials) {
                decryptedPasswords.put(cred.getCredentialid(), credentialService.getDecryptedPassword(cred));
            }
        }
        model.addAttribute("files", files);
        model.addAttribute("notes", notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("decryptedPasswords", decryptedPasswords);
        return "home";
    }

    @GetMapping("/home/delete")
    public String deleteFile(@RequestParam("id") Integer id) {
        fileService.deleteFile(id);
        return "redirect:/home";
    }

    @GetMapping("/home/view")
    public void viewFile(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        File file = fileService.getFileById(id);
        String fileName = file.getFilename();
        String dataDirectory = fileService.getDataDirectory();
        Path filePath = Paths.get(dataDirectory, fileName);
        if (Files.exists(filePath))
        {
            String mimeType = Files.probeContentType(filePath);
            response.setContentType(mimeType);
            try
            {
                Files.copy(filePath, response.getOutputStream());
                response.getOutputStream().flush();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @PostMapping("/home/fileUpload")
    public String fileUpload(@RequestParam("fileUpload") MultipartFile file, Authentication authentication) {
        if (file != null && !file.isEmpty()) {
            User user = userService.getUser(authentication.getName());
            fileService.uploadFile(file, user.getUserid());
        }

        return "redirect:/home";
    }

    @PostMapping("/home/saveNote")
    public String saveNote(Authentication authentication, Note note) {
        User user = userService.getUser(authentication.getName());
        Integer userid = user.getUserid();
        if (note.getNoteid() == null) {
            note.setUserid(userid);
            noteService.saveNote(note);
        } else {
            noteService.updateNote(note);
        }
        return "redirect:/home";
    }

    @GetMapping("/home/deleteNote")
    public String deleteNote(@RequestParam("id") Integer id) {
        noteService.deleteNote(id);
        return "redirect:/home";
    }

    @PostMapping("/home/saveCredential")
    public String saveCredential(Authentication authentication, Credential credential) {
        User user = userService.getUser(authentication.getName());
        Integer userid = user.getUserid();
        credential.setUserid(userid);
        if (credential.getCredentialid() == null) {
            credentialService.saveCredential(credential);
        } else {
            credentialService.updateCredential(credential);
        }
        return "redirect:/home";
    }

    @GetMapping("/home/deleteCredential")
    public String deleteCredential(@RequestParam("id") Integer id) {
        credentialService.deleteCredential(id);
        return "redirect:/home";
    }
}
