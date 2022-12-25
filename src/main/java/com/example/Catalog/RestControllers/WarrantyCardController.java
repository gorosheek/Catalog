package com.example.Catalog.RestControllers;

import com.example.Catalog.Models.WarrantyCard;
import com.example.Catalog.Repositories.UserRepository;
import com.example.Catalog.Repositories.WarrantyCardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(value = "/warrantyCard")
public class WarrantyCardController {
    private final WarrantyCardRepository WarrantyCardRepository;
    private final UserRepository userRepository;
    
    public WarrantyCardController(WarrantyCardRepository WarrantyCardRepository, UserRepository userRepository) {
        this.WarrantyCardRepository = WarrantyCardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "all")
    public ResponseEntity<Iterable<WarrantyCard>> GetAll(@CookieValue(value = "id", defaultValue = "") String id, @RequestParam Boolean isEnded)
    {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        var results = WarrantyCardRepository.findAll();
        var finalResults = new ArrayList<WarrantyCard>();
        results.forEach(x -> {
            System.out.println();
            if (x.getEndDate().before(new Date()) != isEnded) return;
            if (!x.getUser().getId().toString().equals(id)) return;
            finalResults.add(x);
        });
        return ResponseEntity.ok(finalResults);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<WarrantyCard> GetById(@PathVariable Integer id) {
        var WarrantyCard = WarrantyCardRepository.findById(id);
        return WarrantyCard.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(WarrantyCard.get());
    }

    @PostMapping
    public ResponseEntity Create(@CookieValue(value = "id", defaultValue = "") String id, String technicName, String date, String serviceName) {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        var user = userRepository.findById(Integer.parseInt(id));
        if (user.isEmpty()) return ResponseEntity.badRequest().build();

        var card = new WarrantyCard();

        card.setUser(user.get());
        card.setTechnicName(technicName);
        card.setServiceName(serviceName);
        card.setEndDate(new Date(date));

        WarrantyCardRepository.save(card);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity Update(@CookieValue(value = "id", defaultValue = "") String id, String technicName, String date, String serviceName, Integer cardId) {
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        var user = userRepository.findById(Integer.parseInt(id));
        if (user.isEmpty()) return ResponseEntity.badRequest().build();

        var cardRes = WarrantyCardRepository.findById(cardId);
        if (cardRes.isEmpty()) return ResponseEntity.notFound().build();
        var card = cardRes.get();

        card.setUser(user.get());
        card.setTechnicName(technicName);
        card.setServiceName(serviceName);
        var o = date.substring(0, 10).replace('-', '/');
        card.setEndDate(new Date(o));

        WarrantyCardRepository.save(card);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity Delete(@PathVariable Integer id) {
        WarrantyCardRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
