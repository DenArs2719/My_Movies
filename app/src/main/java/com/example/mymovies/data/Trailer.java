package com.example.mymovies.data;

public class Trailer
{
    private String video;
    private String name;

    public Trailer(String video, String name)
    {
        this.video = video;
        this.name = name;
    }

    public String getVideo()
    {
        return video;
    }

    public void setVideo(String video)
    {
        this.video = video;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
