package io.github.flozano.eksidentitypod.sample;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SimpleController {

	private final BucketListing bucketListing;

	public SimpleController(BucketListing bucketListing) {
		this.bucketListing = bucketListing;
	}

	@GetMapping
	public ResponseEntity<List<String>> hello() {
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(bucketListing.listBuckets());
	}
}
