package searchPaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.eCommerce.entity.Product;

public class sortService {

	@Autowired
	ProducRepository productRepository;

	public Page<Product> grid(Integer pageNumber, Integer pageSize, String search) {

		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		if (search != null) {
			return productRepository.findBySearch(search, pageable);
		}
		return productRepository.findAll(pageable);
	}
}
