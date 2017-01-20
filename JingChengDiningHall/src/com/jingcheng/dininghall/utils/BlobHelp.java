package com.jingcheng.dininghall.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.UUID;

import Decoder.BASE64Encoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64DataException;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.StorageUri;
import com.microsoft.azure.storage.blob.*;


public class BlobHelp extends AsyncTask<String, Void, Void> {
	
	public static final String storageConnectionString =
	        "DefaultEndpointsProtocol=https;"
	        + "AccountName=jingchengrestaurant;"
	        + "AccountKey=vgPX6U7Z3+r4X3CRqoX/bfwxaqAZwDccu1EzLKyAHfvtheHwypGy9o7ulhCLE/9+CNJqTPq8r4uGQ29/TWnN/w==";

	private File file;
	private String subMenuName;
	private Handler handler;

	private CloudBlockBlob blob;

	private CloudBlobContainer container;
	
	public BlobHelp(File file,String subMenuName,Handler handler) {
		super();
		this.file = file;
		this.subMenuName = (new BASE64Encoder()).encode(subMenuName.getBytes());
//		this.subMenuName = subMenuName;
		this.handler = handler;
	}


	@Override
	protected Void doInBackground(String... arg0) {
		
		try {
			
//            CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);
//            CloudBlobClient serviceClient = account.createCloudBlobClient();
//
//            
//            CloudBlobContainer container = serviceClient.getContainerReference("blobbasicscontainer"
//                    + UUID.randomUUID().toString().replace("-", ""));
//            
//            
//            // ÈÝÆ÷Ãû±ØÐëÐ¡Ð´
////            CloudBlobContainer container = serviceClient.getContainerReference("localcontainer");
//            container.createIfNotExists();
			
			
			CloudStorageAccount account = CloudStorageAccount.parse(storageConnectionString);

            // Create a blob service client
            CloudBlobClient blobClient = account.createCloudBlobClient();

            // Get a reference to a container
            // The container name must be lower case
            // Append a random UUID to the end of the container name so that
            // this sample can be run more than once in quick succession.
            container = blobClient.getContainerReference("localcontainer");
            	
            // Create the container if it does not exist
            container.createIfNotExists();
            
            BlobContainerPermissions BlobContainerPermissions = new BlobContainerPermissions();
            BlobContainerPermissions.setPublicAccess(BlobContainerPublicAccessType.BLOB);
            container.uploadPermissions(BlobContainerPermissions);
//            container.create();

			
			

            blob = container.getBlockBlobReference("android/"+subMenuName+"/"+file.getName());
//            File sourceFile = new File("c:\\myimages\\image1.jpg");
            blob.upload(new FileInputStream(file), file.length());
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
		
		
		Message message = new Message();
		Bundle bundle = new Bundle();
		String a = blob.getStorageUri().getPrimaryUri().toString();
		bundle.putString("URI", a);
		message.setData(bundle);
		message.what = 8;
		handler.sendMessage(message);
		
		return null;
	}

}
