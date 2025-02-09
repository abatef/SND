package com.snd.storefinder.config;

import com.snd.storefinder.dtos.Location;
import com.snd.storefinder.dtos.product.StoreProductInfo;
import com.snd.storefinder.dtos.user.UserCreationRequest;
import com.snd.storefinder.models.User;
import com.snd.storefinder.models.store.StoreProduct;
import com.snd.storefinder.models.store.StoreProductId;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Converter<Point, Location> pointLocationConverter = ctx -> {
            Point point = ctx.getSource();
            return Location.of(point);
        };

        Converter<Location, Point> locationPointConverter = ctx -> {
            Location location = ctx.getSource();
            return location.toPoint();
        };

        Converter<StoreProductInfo, StoreProduct> storeProductConverter = ctx -> {
            StoreProductInfo storeProductInfo = ctx.getSource();
            StoreProduct storeProduct = new StoreProduct();
            StoreProductId id = new StoreProductId();
            id.setProductId(storeProductInfo.getProductId());
            id.setStoreId(storeProductInfo.getStoreId());
            storeProduct.setId(id);
            return storeProduct;
        };

        Converter<StoreProduct, StoreProductInfo> productInfoConverter = ctx -> {
            StoreProduct storeProduct = ctx.getSource();
            StoreProductInfo info = new StoreProductInfo();
            info.setPrice(storeProduct.getPrice());
            info.setQuantity(storeProduct.getQuantity());
            info.setProductId(storeProduct.getProduct().getId());
            info.setStoreId(storeProduct.getStore().getId());
            info.setStoreName(storeProduct.getStore().getName());
            return info;
        };

        modelMapper.createTypeMap(Point.class, Location.class).setConverter(pointLocationConverter);
        modelMapper.createTypeMap(Location.class, Point.class).setConverter(locationPointConverter);
        modelMapper.createTypeMap(StoreProductInfo.class, StoreProduct.class).setConverter(storeProductConverter);
        modelMapper.createTypeMap(StoreProduct.class, StoreProductInfo.class).setConverter(productInfoConverter);
        return modelMapper;
    }
}
