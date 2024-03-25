package io.github.flozano.eksidentitypod.sample;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

	private final BucketListing bucketListing;

	public SimpleController(BucketListing bucketListing) {
		this.bucketListing = bucketListing;
	}

	@GetMapping("/{region}")
	public ResponseEntity<List<String>> listRegion(@PathVariable(value="region") String region) {
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bucketListing.listBuckets(region));
	}

	@GetMapping("/")
	public ResponseEntity<List<String>> listDefaultRegion() {
		return listRegion("us-east-1");
	}
}
