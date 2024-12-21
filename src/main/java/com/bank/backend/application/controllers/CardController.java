package com.bank.backend.application.controllers;

import com.bank.backend.application.dtos.card.CreateCardRequest;
import com.bank.backend.application.dtos.card.CreateCardResponse;
import com.bank.backend.application.dtos.card.UpdateCardRequest;
import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.mapper.CardMapper;
import com.bank.backend.domain.model.Card;
import com.bank.backend.domain.services.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/cards")
@RestController
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final CardMapper cardMapper;

    @PostMapping
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody CreateCardRequest createCardRequest) {
        Card card = cardMapper.requestToModel(createCardRequest);
        return ResponseEntity.ok(cardMapper.modelToResponse(cardService.createCard(card)));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable Long id){
        return ResponseEntity.ok(cardService.getCard(id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<CardStatus> setCardStatus(@PathVariable Long id,@RequestParam CardStatus cardStatus){
        return ResponseEntity.ok(cardService.setCardStatus(id,cardStatus));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Card> updateCardInfo(@PathVariable Long id, @RequestBody UpdateCardRequest updateCardRequest){
        Card card= cardMapper.requestToModel(updateCardRequest);
        return ResponseEntity.ok(cardService.updateCardInfo(id,card));
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards(){
        return  ResponseEntity.ok(cardService.getAllCards());
    }
}



