
DOMAIN
    LOOP
        Start Loop              (done)
        End Loop                (done)
        ON STARTUP
            Create Users        (done)
            Create Decks        (done)
            Create Cards        (done)
            Create Collabs      (done)
            Accept Collabs      (done)
        RANDOM BEHAVIOR
            Select Action       (done)
            Review Cards        (done)
            Create User         (done)
            Create Cards        (done)
            Override Cards      (done)
            Create Decks        (done)
            Create Collabs      (done)
            Accept Collabs      (done)

WEB
    USER-Service
        Create User             (done)
    DECK-Service
        Create Deck             (done)
        Create Card             (done)
        Override Card           (done)
        Review Card             (done)
        Fetch Card              (done)
    COLLAB-Service
        Start Collaboration     (done)
        Accept Collaboration    (done)
        End Collaboration       (done)

CONTROLLER
    LOOP
        Start Loop              (done)
        End Loop                (done)


TODO
    synchronize decks periodically
    deck - isActive status
    refactor services to UUIDs 
