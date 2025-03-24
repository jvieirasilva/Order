package com.order.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.order.dto.ProductDTO;
import com.order.model.Product;
import com.order.repository.ProductRepository;
import com.order.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository repository;

    private static final String IMAGE_DIR = "C:/Java-Developer/React/order/public/images/";

    public Page<ProductDTO> findAll(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products;

        if (name == null || name.trim().isEmpty()) {
            // Busca todos os produtos
            products = repository.findAll(pageable);
        } else {
            // Busca com filtro por nome (case insensitive, contém)
            products = repository.findByNameContainingIgnoreCase(name, pageable);
        }
        return products.map(this::toDTO);
    }

    public ProductDTO findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        return toDTO(product);
    }

    public ProductDTO save(@Valid ProductDTO dto) {
        List<String> imagePaths = saveImages(dto.getImageFiles()); // Upload das imagens

        Product product = new Product(null, dto.getName(), imagePaths, dto.getPrice());
        product = repository.save(product);

        return toDTO(product);
    }

    public ProductDTO update(Long id, @Valid ProductDTO dto) {
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        existingProduct.setName(dto.getName());
        existingProduct.setPrice(dto.getPrice());

        // Se houver novas imagens enviadas, fazemos o upload
        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            List<String> newImages = saveImages(dto.getImageFiles());
            existingProduct.setImages(newImages);
        }

        return toDTO(repository.save(existingProduct));
    }

    public void delete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        repository.delete(product);
    }

    // Método para salvar as imagens no diretório local
    private List<String> saveImages(List<MultipartFile> imageFiles) {
        if (imageFiles.isEmpty()) {
            return List.of();
        }

        return imageFiles.stream().map(file -> {
            try {
                // Criando um nome único para evitar duplicatas
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

                // Caminho onde a imagem será salva
                Path path = Paths.get(IMAGE_DIR + fileName);

                // Criando diretório se não existir
                File directory = new File(IMAGE_DIR);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Salvando a imagem no disco
                file.transferTo(path.toFile());

                return "/images/" + fileName; // Retorna apenas o caminho relativo
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar imagem: " + e.getMessage());
            }
        }).collect(Collectors.toList());
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getImages(), product.getPrice(), null);
    }
}

