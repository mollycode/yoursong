package com.yoursong.android.Http;

import com.yoursong.android.Models.Track;

import java.util.List;

public class QueryResponse {

    public Message message;

    public class Message {

        public ResponseHeader header;
        public ResponseBody body;

        public class ResponseHeader {
            public int status_code;
            public int available;
        }

        public class ResponseBody {
            public List<TrackInfo> track_list;

            public class TrackInfo {
                public Track track;
            }

        }

    }
}
