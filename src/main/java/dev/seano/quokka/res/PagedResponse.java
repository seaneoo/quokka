package dev.seano.quokka.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {

	private int page;

	@JsonProperty("total_pages")
	private int totalPages;

	private int size;

	@JsonProperty("total_results")
	private long totalResults;

	private List<T> results;

	public PagedResponse(Page<T> page) {
		this.page = page.getNumber() + 1;
		this.totalPages = page.getTotalPages();
		this.size = page.getSize();
		this.totalResults = page.getTotalElements();
		this.results = page.getContent();
	}
}
