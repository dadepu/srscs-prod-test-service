package de.danielkoellgen.srscsprodtestservice.domain.loop.domain;

import de.danielkoellgen.srscsprodtestservice.domain.loop.application.LoopService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "loops")
@NoArgsConstructor
@AllArgsConstructor
public class Loop {

    @Id
    @Getter
    @Column(name = "loop_id")
    private @NotNull Integer loopId;

    @Getter
    @Column(name = "loop_status")
    private @NotNull LoopStatus loopStatus;


    public Loop(@NotNull LoopStatus loopStatus) {
        this.loopId = LoopService.loopId;
        this.loopStatus = loopStatus;
    }

    public void startLoop() {
        loopStatus = LoopStatus.STARTED;
    }

    public void stopLoop() {
        loopStatus = LoopStatus.STOPPED;
    }
}
