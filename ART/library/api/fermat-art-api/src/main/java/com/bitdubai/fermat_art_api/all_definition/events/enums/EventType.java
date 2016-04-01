package com.bitdubai.fermat_art_api.all_definition.events.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEventEnum;
import com.bitdubai.fermat_api.layer.all_definition.events.common.GenericEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventMonitor;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.events.ArtistConnectionRequestNewsEvent;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.events.ArtistConnectionRequestUpdatesEvent;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 31/03/16.
 */
public enum EventType implements FermatEventEnum {

    ARTIST_CONNECTION_REQUEST_NEWS("ARCRN"){
        public final FermatEvent getNewEvent() { return new ArtistConnectionRequestNewsEvent(this); }
    },
    ARTIST_CONNECTION_REQUEST_UPDATES("ACRU"){
        public final FermatEvent getNewEvent(){ return new ArtistConnectionRequestUpdatesEvent(this);}
    }
    ;

    private final String code;

    EventType(String code) {
        this.code = code;
    }

    @Override // by default
    public FermatEventListener getNewListener(FermatEventMonitor fermatEventMonitor) { return new GenericEventListener(this, fermatEventMonitor); }

    @Override
    public final String getCode() {
        return this.code;
    }

    @Override
    public final Platforms getPlatform() {
        return Platforms.ART_PLATFORM;
    }

}
