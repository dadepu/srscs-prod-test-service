package de.danielkoellgen.srscsprodtestservice.web.deckservice;

import de.danielkoellgen.srscsprodtestservice.domain.deck.domain.Deck;
import de.danielkoellgen.srscsprodtestservice.domain.domainprimitive.DeckName;
import de.danielkoellgen.srscsprodtestservice.domain.user.domain.User;
import de.danielkoellgen.srscsprodtestservice.web.deckservice.dto.DeckRequestDto;
import de.danielkoellgen.srscsprodtestservice.web.deckservice.dto.DeckResponseDto;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@Scope("singleton")
public class DeckClient {

    private final WebClient deckClient;

    private final String deckServiceAddress;

    private final Logger logger = LoggerFactory.getLogger(DeckClient.class);

    @Autowired
    public DeckClient(@Value("${app.deckService.address}") String deckServiceAddress) {
        this.deckClient = WebClient.create();
        this.deckServiceAddress = deckServiceAddress;
    }

    public @NotNull Optional<Deck> createDeck(User user, DeckName deckName) {
        DeckRequestDto requestDto = new DeckRequestDto(user.getUserId(), deckName.getName());

        logger.debug("Requesting Deck-Service to create a new Deck. Address is POST {}",
                deckServiceAddress+"/decks");
        logger.trace("{}", requestDto);

        try {
            DeckResponseDto responseDto = deckClient.post().uri(deckServiceAddress + "/decks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(requestDto)
                    .retrieve()
                    .onStatus(httpStatus -> httpStatus != HttpStatus.CREATED, clientResponse ->
                            clientResponse.createException().flatMap(Mono::error))
                    .bodyToMono(DeckResponseDto.class)
                    .block();
            assert responseDto != null;
            logger.debug("Request successful.");
            logger.trace("{}", responseDto);
            return Optional.of(new Deck(responseDto.deckId(), user, responseDto.isActive()));

        } catch (WebClientResponseException e) {
            logger.error("Request failed. {}", e.getMessage());
            return Optional.empty();

        } catch (Exception e) {
            logger.error("Request failed. {}", e.getMessage());
            return Optional.empty();
        }
    }
}
