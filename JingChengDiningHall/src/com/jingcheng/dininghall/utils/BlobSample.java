package com.jingcheng.dininghall.utils;

import java.io.*;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class BlobSample {
	/**
	 * DefaultEndpointsProtocol=https;
	 * AccountName=jingchengrestaurant;
	 * AccountKey=vgPX6U7Z3+r4X3CRqoX/bfwxaqAZwDccu1EzLKyAHfvtheHwypGy9o7ulhCLE/9+CNJqTPq8r4uGQ29/TWnN/w==
	 */
    public static final String storageConnectionString =
        "DefaultEndpointsProtocol=https;"
        + "AccountName=jingchengrestaurant;"
        + "AccountKey= vgPX6U7Z3+r4X3CRqoX/bfwxaqAZwDccu1EzLKyAHfvtheHwypGy9o7ulhCLE/9+CNJqTPq8r4uGQ29/TWnN/w==";

    public static void main(String[] args) {
        try {
            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
            CloudBlobClient serviceClient = account.createCloudBlobClient();

            // ����������Сд
            CloudBlobContainer container = serviceClient.getContainerReference("localcontainer");
            container.createIfNotExists();

            // �ϴ�һ��ͼ���ļ���
            CloudBlockBlob blob = container.getBlockBlobReference("image1.jpg");
            File sourceFile = new File("c:\\myimages\\image1.jpg");
            blob.upload(new FileInputStream(sourceFile), sourceFile.length());

            // ����ͼ���ļ���
            File destinationFile = new File(sourceFile.getParentFile(), "image1Download.tmp");
            blob.downloadToFile(destinationFile.getAbsolutePath());
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.out.print("FileNotFoundException encountered: ");
            System.out.println(fileNotFoundException.getMessage());
            System.exit(-1);
        }
        catch (StorageException storageException) {
            System.out.print("StorageException encountered: ");
            System.out.println(storageException.getMessage());
            System.exit(-1);
        }
        catch (Exception e) {
            System.out.print("Exception encountered: ");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}