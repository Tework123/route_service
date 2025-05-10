CREATE TABLE geozone (
                         geozone_id UUID PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         geometry GEOMETRY(Polygon, 4326) NOT NULL,
                         restaurant_id UUID,
                         time_create TIMESTAMP NOT NULL,
                         time_update TIMESTAMP
                     );

CREATE TABLE work_shift_session (
                                    work_shift_session_id UUID PRIMARY KEY,
                                    user_id UUID NOT NULL,
                                    start_time TIMESTAMP NOT NULL,
                                    end_time TIMESTAMP,
                                    work_shift_session_status VARCHAR(255),
                                    time_create TIMESTAMP NOT NULL
);

CREATE TABLE location_point (
                                location_point_id UUID PRIMARY KEY,
                                work_shift_session_id UUID NOT NULL,
                                location GEOMETRY(Point, 4326) NOT NULL,
                                timestamp TIMESTAMP NOT NULL,
                                time_create TIMESTAMP NOT NULL,
                                CONSTRAINT fk_location_point_work_shift_session FOREIGN KEY (work_shift_session_id) REFERENCES work_shift_session(work_shift_session_id)
);

CREATE TABLE route_event (
                             route_event_id UUID PRIMARY KEY,
                             work_shift_session_id UUID,
                             location_point_id UUID,
                             order_id UUID,
                             route_event_status VARCHAR(255),
                             time_create TIMESTAMP NOT NULL,
                             message VARCHAR(255),
                             CONSTRAINT fk_route_event_work_shift_session FOREIGN KEY (work_shift_session_id) REFERENCES work_shift_session(work_shift_session_id),
                             CONSTRAINT fk_route_event_location_point FOREIGN KEY (location_point_id) REFERENCES location_point(location_point_id)
);
