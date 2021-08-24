package com.loco.photo.locophoto.controller

import com.loco.photo.locophoto.tools.GoogleMaps
import com.loco.photo.locophoto.repository.ImageRepository
import com.loco.photo.locophoto.repository.UserRepository
import com.loco.photo.locophoto.bean.Image
import com.loco.photo.locophoto.bean.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import ch.qos.logback.classic.Logger

@RestController
class ImageController {

    @Autowired
    ImageRepository imageRepository

    @Autowired
    UserRepository userRepository

    Logger logger = LoggerFactory.getLogger(ImageController.class)

    @GetMapping("/images")
    List<Image> index() {
        logger.info("Getting All Images")
        return imageRepository.findAll()
    }

    @GetMapping("/allImages/{lat}/{lng}")
    List<Image> search(@PathVariable("lat") String lat, @PathVariable("lng") String lng) {
        String city = GoogleMaps.getCity(lat, lng)
        logger.info("Getting images for city " + city + ". Using lat : " + lat + " Long: " + lng)
        return imageRepository.findByCityContaining(city)
    }

    @GetMapping("/userImages/{email}")
    List<Image> searchByUser(@PathVariable("email") String email) {
        Optional<User> user = userRepository.findDistinctEmail(email)
        String id = "0";
        user.ifPresent({ u -> id = u.id })
        return imageRepository.findByUserID(id)
    }

    @PostMapping("/image")
    Image create(@RequestBody Map<String, String> body) {

        Double lat = body.get("lat")
        Double lng = body.get("lng")
        GoogleMaps.getCity(body.get("lat"), body.get("lng"))
        String city = GoogleMaps.getCity(lat, lng)
        String userId = body.get("userId")
        String comment = body.get("comment")
        String date = body.get("date")
        String url = body.get("url")
        logger.info("Creating new Image for " + city + " on "  + date)
        return imageRepository.save(new Image(lat, lng, city, userId, date, url, comment))
    }

    @PostMapping("/image/views/{id}")
    Image updateViews(@PathVariable String id) {
        Long num = Long.parseLong(id)
        Optional<Image> images = imageRepository.findById(num)
        int views = images.get().getViews() == null ? 0 : images.get().getViews().intValue() + 1
        images.get().setViews(views)
        imageRepository.save(images.get())
    }

    @PostMapping("/image/likes/{id}")
    Image updateLikes(@PathVariable String id) {
        Long num = Long.parseLong(id)
        Optional<Image> images = imageRepository.findById(num)
        int likes = images.get().getLikes() == null ? 0 : images.get().getLikes().intValue() + 1
        images.get().setLikes(likes)
        imageRepository.save(images.get())
    }

    @PostMapping("/image/unlike/{id}")
    Image updateUnLikes(@PathVariable String id) {
        Long num = Long.parseLong(id)
        Optional<Image> images = imageRepository.findById(num)
        int likes = images.get().getLikes() == null ? 0 : images.get().getLikes().intValue() - 1
        images.get().setLikes(likes)
        imageRepository.save(images.get())
    }


    @PostMapping("/image/delete/{id}")
    Image deleteImage(@PathVariable String id) {
        Long num = Long.parseLong(id)
        Optional<Image> images = imageRepository.findById(num)
        Image image = images.get()
        image.setDeleted(true)
        imageRepository.save(image)
    }
}

