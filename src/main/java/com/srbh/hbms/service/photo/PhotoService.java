package com.srbh.hbms.service.photo;

import com.srbh.hbms.model.entity.Photo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoService {

    List<Photo> getAllPhotos();

    Photo getPhoto(int id) throws Exception;

    Photo addPhoto(Photo photo);

    List<Photo> addAllPhotos(List<Photo> photos);

    Photo removePhoto(int id) throws Exception;

}