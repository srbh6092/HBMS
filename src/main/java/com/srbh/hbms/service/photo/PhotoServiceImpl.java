package com.srbh.hbms.service.photo;

import com.srbh.hbms.model.entity.Photo;
import com.srbh.hbms.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService{

    @Autowired
    PhotoRepository photoRepository;

    @Override
    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    @Override
    public Photo getPhoto(int id) throws Exception {
        return null;
    }

    @Override
    public Photo addPhoto(Photo photo) {
        return photoRepository.save(photo);

    }

    @Override
    public List<Photo> addAllPhotos(List<Photo> photos) {
        return photoRepository.saveAll(photos);
    }

    @Override
    public Photo removePhoto(int id) throws Exception {
        return null;
    }

}