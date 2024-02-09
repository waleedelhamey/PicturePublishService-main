package com.example.picturepublish.controller;

import com.example.picturepublish.entity.Pictrue;
import com.example.picturepublish.repository.PictrueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PictureController {
    @Autowired
    PictrueRepository pictrueRepository;

    @GetMapping("/acceptedPictures")
    public List<Pictrue> getAcceptedPictures(){
        return this.pictrueRepository.findByStatus("accepted");
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/acceptedPictures/{id}")
    public Pictrue accept(@PathVariable long id){
        return this.pictrueRepository.findById(id)
                .map(pictrue -> {
                    pictrue.setStatus("accepted");
                    return this.pictrueRepository.save(pictrue);
                })
                .orElseGet(() -> {
                    return null;
                });

    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/rejectedPictures")
    public List<Pictrue> getRejectedPictures(){
        return this.pictrueRepository.findByStatus("rejected");
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping("/rejectedPictures/{id}")
    public Pictrue reject(@PathVariable long id){
        return this.pictrueRepository.findById(id)
                .map(pictrue -> {
                    pictrue.setStatus("rejected");
                    return this.pictrueRepository.save(pictrue);
                })
                .orElseGet(() -> {
                    return null;
                });

    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping("/pictures")
    public Iterable <Pictrue> getAllPictures(){
        return this.pictrueRepository.findAll();
    }
}
