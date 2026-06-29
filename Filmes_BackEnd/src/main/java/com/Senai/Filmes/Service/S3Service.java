package com.Senai.Filmes.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    private static final List<String> TIPOS_PERMITIDOS = List.of("image/jpeg", "image/png", "image/webp");

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket:cinemaapifilmes}")
    private String bucket;

    @Value("${aws.region:us-east-1}")
    private String region;

    public String upload(MultipartFile arquivo) {
        validarArquivo(arquivo);

        String extensao = extrairExtensao(arquivo.getOriginalFilename());
        String chave = "posters/" + UUID.randomUUID() + extensao;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(chave)
                    .contentType(arquivo.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(arquivo.getBytes()));
        } catch (IOException e) {
            throw new IllegalStateException("Falha ao fazer upload da imagem: " + e.getMessage());
        }

        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + chave;
    }

    public void deletar(String url) {
        if (url == null || url.isBlank()) return;

        String prefixo = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        if (!url.startsWith(prefixo)) return;

        String chave = url.substring(prefixo.length());

        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(chave)
                .build());
    }

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo de imagem não pode ser vazio");
        }
        if (!TIPOS_PERMITIDOS.contains(arquivo.getContentType())) {
            throw new IllegalArgumentException("Tipo de arquivo não permitido. Use JPEG, PNG ou WebP");
        }
    }

    private String extrairExtensao(String nomeArquivo) {
        if (nomeArquivo == null || !nomeArquivo.contains(".")) return "";
        return nomeArquivo.substring(nomeArquivo.lastIndexOf("."));
    }
}
