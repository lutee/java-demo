package cn.chenyuxian.file;

import java.io.File;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

public class COSFileUpload {
	public static void main(String[] args) {
		String secretId = "AKIDCCwUosfAON5XieKkunPkob7MQEl10iAN";
		String secretKey = "TgcHfmcDYcQzGH1UhdKktlMp66pGRLyJ";
		COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
		Region region = new Region("ap-nanjing");
		ClientConfig clientConfig = new ClientConfig(region);
		clientConfig.setHttpProtocol(HttpProtocol.https);
		COSClient cosClient = new COSClient(cred, clientConfig);
		
		List<Bucket> buckets = cosClient.listBuckets();
		for (Bucket bucketElement : buckets) {
			String bucketName = bucketElement.getName();
			String bucketLocation = bucketElement.getLocation();
			System.out.println(bucketName + "," + bucketLocation);
		}

//		File localFile = new File("E:\\3333\\145505.zip");
		String bucketName = "yxcling-1300757405";
//		String key = "demo";
//		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
//		PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
		listObjectsRequest.setBucketName(bucketName);
//		listObjectsRequest.setPrefix("cos-access-log/");
		listObjectsRequest.setDelimiter("/");
		listObjectsRequest.setMaxKeys(1000);
		ObjectListing objectListing = null;
		do {
			try {
				objectListing = cosClient.listObjects(listObjectsRequest);
			} catch (CosServiceException e) {
				e.printStackTrace();
				return;
			} catch (CosClientException e) {
				e.printStackTrace();
				return;
			}

			List<String> commonPrefixs = objectListing.getCommonPrefixes();
			List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
			for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
				String ekey = cosObjectSummary.getKey();
				String etag = cosObjectSummary.getETag();
				long fileszie = cosObjectSummary.getSize();
				String storageClasses = cosObjectSummary.getStorageClass();
				System.out.println(ekey + "," + etag + "," + fileszie + "," + storageClasses);
			}
			String nextMarker = objectListing.getNextMarker();
			listObjectsRequest.setMarker(nextMarker);
		} while (objectListing.isTruncated());
		
		System.out.println(cosClient.getObjectUrl(bucketName, "core.pdf"));
		
	}
}
