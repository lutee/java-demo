package cn.chenyuxian.file;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import io.minio.BucketExistsArgs;
import io.minio.DeleteBucketEncryptionArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Bucket;

public class MinioFactory {

	private String endpoint = "1.15.68.129";

	private Integer port = 9000;

	private String accessKey = "minio";

	private String secretKey = "chenyuxian.cn";

	public static MinioClient minioClient = null;

	public static void main(String[] args)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException, IOException {
		try {
			MinioClient minioClient = MinioClient.builder().endpoint("1.15.68.129", 9000, false)
					.credentials("minio", "chenyuxian.cn").build();
			boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("demo").build());
			if (!found) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket("demo").build());
			} else {
				System.out.println("Bucket 'demo' alreay exists");
			}

			minioClient.uploadObject(UploadObjectArgs.builder().bucket("demo").object("demo.zip")
					.filename("E:\\3333\\Discuz_X3.4_SC_UTF8_20210630.zip").build());
			System.out.println("success");
		} catch (MinioException e) {
			System.out.println("Error occurred: " + e);
			System.out.println("HTTP trace:" + e.httpTrace());
		}
	}

	/**
	 * 创建minio客户端
	 * 
	 * @return
	 */
	public MinioClient createMinioClient() {
		return MinioClient.builder().endpoint(endpoint, port, false).credentials(accessKey, secretKey).build();
	}

	/**
	 * 创建bucket桶
	 * @param bucketName
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void createBucket(String bucketName)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException, IOException {
		try {
			boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
			if (!found) {
				minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
			} else {
				System.out.println("Bucket" + bucketName + " alreay exists");
			}
		} catch (MinioException e) {
			System.out.println("Error occurred: " + e);
			System.out.println("HTTP trace:" + e.httpTrace());
		}
	}
	
	/**
	 * 删除bucket桶
	 * @param bucketName
	 * @throws InvalidKeyException 不合法的存储桶名称
	 * @throws ErrorResponseException
	 * @throws InsufficientDataException
	 * @throws InternalException
	 * @throws InvalidResponseException
	 * @throws NoSuchAlgorithmException
	 * @throws ServerException
	 * @throws XmlParserException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void deleteBucket(String bucketName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
		minioClient.deleteBucketEncryption(DeleteBucketEncryptionArgs.builder().bucket(bucketName).build());
	}
	
	public List<Bucket> list() throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IOException{
		List<Bucket> buckets = minioClient.listBuckets();
		for(Bucket bucket : buckets) {
			System.out.println(bucket);
		}
		return buckets;
	}
}
