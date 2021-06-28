package com.loco.photo.locophoto.tools

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.PutObjectRequest
import com.loco.photo.locophoto.controller.ImageUploader
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

import javax.annotation.PostConstruct

@Service
class AmazonClient implements ImageUploader {

    private AmazonS3 s3client

    private String ENDPOINT_URL = S3Credentials.ENDPOINT
    private String BUCKET_NAME = S3Credentials.BUCKET_NAME
    private String ACCESS_KEY = S3Credentials.ACCESS_KEY_ID
    private String SECRET_ACCESS_KEY = S3Credentials.SECRET_ACCESS_KEY

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.ACCESS_KEY, this.SECRET_ACCESS_KEY)
        this.s3client = new AmazonS3Client(credentials)
    }

    @Override
    String uploadImage(Object object) {

        MultipartFile multipartFile = (MultipartFile) object
        String fileUrl = ""
        try {
            File file = convertMultiPartToFile(multipartFile)
            String fileName = generateFileName(multipartFile)
            fileUrl = ENDPOINT_URL + "/" + BUCKET_NAME + "/" + fileName
            uploadFileTos3bucket(fileName, file)
            file.delete()
        } catch (Exception e) {
            e.printStackTrace()
        }
        return fileUrl
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename())
        FileOutputStream fos = new FileOutputStream(convFile)
        fos.write(file.getBytes())
        fos.close()
        return convFile
    }

    @Override
    String deleteFileFromStorage(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1)
        s3client.deleteObject(new DeleteObjectRequest(BUCKET_NAME + "/", fileName))
        return "Successfully deleted"
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_")
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead))
    }
}