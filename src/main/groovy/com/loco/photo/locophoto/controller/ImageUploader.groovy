package com.loco.photo.locophoto.controller;

interface ImageUploader {

    String uploadImage(Object object)

    String deleteFileFromStorage(String fileUrl)
}

