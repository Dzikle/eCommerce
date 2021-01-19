package searchPaging;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.eCommerce.entity.Product;

@Controller
public class PagingController {
	
	@Autowired
	sortService productServiceImpl;

	@GetMapping("/pag/{pagNum}")
	   public String gridDetails( Model model ,@PathVariable("pagNum") Integer pagNum,
			@Param("search")String search
		) {
		

	    Integer pageSize = 3;
	    
	    Page<Product>pag = productServiceImpl.grid(pagNum, pageSize,search);
	    
	    List<Product> listProducts = pag.getContent();
	  
	      model.addAttribute("listProducts", listProducts);
	   	  model.addAttribute("currentPage",pagNum);
		  model.addAttribute("totalPages", pag.getTotalPages());
		  model.addAttribute("totalItems", pag.getTotalElements());
		  model.addAttribute("search", search);
		
		       return "productGrid";
	}
}
