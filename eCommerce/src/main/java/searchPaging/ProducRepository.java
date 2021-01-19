package searchPaging;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.eCommerce.entity.Product;

@Repository
public interface ProducRepository extends PagingAndSortingRepository<Product, Integer> {
	
	@Query("SELECT p FROM Product p WHERE CONCAT(p.productName, ' ', p.productPrice, ' ', p.keyword) LIKE %?1%")
	@Transactional(readOnly = true)
	Page<Product> findBySearch(String search,Pageable pageable );
}
