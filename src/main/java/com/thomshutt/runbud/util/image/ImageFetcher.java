package com.thomshutt.runbud.util.image;

import com.thomshutt.runbud.core.Run;
import com.thomshutt.runbud.data.RunDAO;

public interface ImageFetcher {

    public void fetchImageUrl(Run run, long runId, double latitude, double longitude);

}
