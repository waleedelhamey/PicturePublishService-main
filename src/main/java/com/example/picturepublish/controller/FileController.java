package com.example.picturepublish.controller;

import com.example.picturepublish.entity.Pictrue;
import com.example.picturepublish.entity.UserInfo;
import com.example.picturepublish.model.UploadFileResponse;
import com.example.picturepublish.repository.PictrueRepository;
import com.example.picturepublish.repository.UserRepository;
import com.example.picturepublish.services.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final FileStorageService fileStorageService;

    private final PictrueRepository pictrueRepository;

    private final UserRepository userRepository;

    @Autowired
    public FileController(FileStorageService fileStorageService, PictrueRepository pictrueRepository, UserRepository userRepository) {
        this.fileStorageService = fileStorageService;
        this.pictrueRepository = pictrueRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"},path = "/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("description") String description,
                                         @RequestParam("category") String category,
                                         @RequestParam("user") Long userid
    ){
        if(file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png") || file.getContentType().equals("image/gif") ) {
            String fileName = fileStorageService.storeFile(file);
            logger.info(fileName);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName)
                    .toUriString();

            Optional<UserInfo> pictureUser = userRepository.findById(userid);
            if(pictureUser.isEmpty())
                throw new IllegalStateException("No User Found") ;
            logger.info(pictureUser.get().toString());
            pictrueRepository.save(Pictrue.builder()
                    .description(description).category(category).fileurl(fileDownloadUri).userInfo(pictureUser.get()).status("pending")
                    .build());

            return new UploadFileResponse(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize());
        }else{
            return new UploadFileResponse("This File not accepted", "Un Supported Type",
                    file.getContentType(), file.getSize());
        }
    }



}
