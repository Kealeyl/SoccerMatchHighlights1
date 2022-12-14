package com.soccermatchhighlights;

import java.io.Serializable;
import java.util.List;


public class Match implements Serializable {

    private String title;
    private String embed;
    private String url;
    private String thumbnail;
    private String date;
    private teamADTO teamA;
    private teamBDTO teamB;
    private boolean isFavourite;
    private List<VideosDTO> videos;
    private CompetitionDTO competition;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public teamADTO getTeamA() {
        return teamA;
    }

    public void setTeamA(teamADTO teamA) {
        this.teamA = teamA;
    }

    public teamBDTO getTeamB() {
        return teamB;
    }

    public void setTeamB(teamBDTO teamB) {
        this.teamB = teamB;
    }

    public CompetitionDTO getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionDTO competition) {
        this.competition = competition;
    }

    public List<VideosDTO> getVideos() {
        return videos;
    }

    public void setVideos(List<VideosDTO> videos) {
        this.videos = videos;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public static class teamADTO implements Serializable {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "teamADTO{" +
                    "name='" + name + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class teamBDTO implements Serializable {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "teamBDTO{" + "name='" + name + '\'' + ", url='" + url + '\'' + '}';
        }
    }

    public static class CompetitionDTO implements Serializable {
        private String name;
        private Integer id;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "CompetitionDTO{" + "name='" + name + '\'' + ", id=" + id + ", url='" + url +
                    '\'' + '}';
        }
    }

    public static class VideosDTO implements Serializable {
        private String title;
        private String embed;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEmbed() {
            return embed;
        }

        public void setEmbed(String embed) {
            this.embed = embed;
        }

        @Override
        public String toString() {
            return "VideosDTO{" + "title='" + title + '\'' + ", embed='" + embed + '\'' + '}';
        }
    }

    @Override
    public String toString() {
        return "SoccerMatch{" + "title='" + title + '\'' + ", embed='" + embed + '\'' + ", url='" +
                url + '\'' + ", thumbnail='" + thumbnail + '\'' + ", date='" + date + '\'' +
                ", teamA=" + teamA + ", teamB=" + teamB + ", competition=" + competition +
                ", videos=" + videos + '}';
    }
}
