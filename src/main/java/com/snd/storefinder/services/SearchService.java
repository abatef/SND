package com.snd.storefinder.services;

import com.snd.storefinder.models.store.Product;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final ElasticsearchOperations elasticsearchOperations;

    public SearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void saveProduct(Product product) {
        elasticsearchOperations.save(product);
    }

    public List<Product> search(@NotNull String name, String category, int page, int size) {
        Criteria criteria = Criteria.where("name").fuzzy(name)
                .or(Criteria.where("description").fuzzy(name));
        if (category != null && !category.isEmpty()) {
            criteria.and("category").fuzzy(category);
        }
        Pageable pageable = PageRequest.of(page, size);
        CriteriaQuery query = new CriteriaQuery(criteria).setPageable(pageable);
        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    public List<Product> searchByCategory(String category) {
        Criteria criteria = Criteria.where("category").is(category).fuzzy("category");
        CriteriaQuery query = new CriteriaQuery(criteria);

        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
