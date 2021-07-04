package com.loco.photo.locophoto.controller

import com.loco.photo.locophoto.tools.ImageUploader
import com.loco.photo.locophoto.tools.AmazonClient
import com.loco.photo.locophoto.tools.GoogleMaps
import com.loco.photo.locophoto.repository.ImageRepository
import com.loco.photo.locophoto.bean.Image
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RestController
@RequestMapping("/storage/")

class BucketController {

    @Autowired
    ImageRepository imageRepository

    private ImageUploader imageUploader

    @Autowired
    BucketController(   AmazonClient imageUploader) {
        this.imageUploader = imageUploader
    }

    @PostMapping("/uploadFile")
    String uploadFile(@RequestPart(value = "file") MultipartFile file, @RequestParam String lat,  @RequestParam String lng, @RequestParam String userID, @RequestParam String comment) {
        String url = this.imageUploader.uploadImage(file)
        Double latitude = Double.parseDouble(lat)
        Double longitude = Double.parseDouble(lng)
        String city =  GoogleMaps.getCity(lat, lng)
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        LocalDate localDate = LocalDate.now()
        Image image =  imageRepository.save(new Image(latitude, longitude, city, userID, dateTimeFormatter.format(localDate).toString(), url, comment))
        if (url != null && image != null) {
            return "Image Uploaded!!!"
        } else {
            return "Sorry something went wrong"
        }
    }

    @DeleteMapping("/deleteFile")
    String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.imageUploader.deleteFileFromStorage(fileUrl)
    }

}
