package com.shortlinkv1.Backend.controller.qrCodeController;

import com.shortlinkv1.Backend.entity.linkEntity.ShortLink;
import com.shortlinkv1.Backend.repository.ShortLink.LinkRepository;
import com.shortlinkv1.Backend.service.qrCode.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/qr")
public class QrCodeRestController {

    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    LinkRepository linkRepository;

    @GetMapping("/{code}")
    public ResponseEntity<byte[]> getQrCode(@PathVariable String code){
        System.out.println("🔍 Запрос на QR для кода: " + code);

        Optional<ShortLink> linkOptional = linkRepository.findByShortCode(code);
        if(linkOptional.isEmpty()){
            System.out.println("❌ Ссылка не найдена");
            return ResponseEntity.notFound().build();
        }

        try {
            String redirectUrl = "http://localhost:8080/s/" + code;
            System.out.println("✅ Генерируем QR для: " + redirectUrl);

            byte[] qrImage = qrCodeService.generateQrCode(redirectUrl, 300, 300);
            System.out.println("✅ Изображение сгенерировано, размер: " + qrImage.length + " байт");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("inline", "qrcode.png");

            return new ResponseEntity<>(qrImage, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
