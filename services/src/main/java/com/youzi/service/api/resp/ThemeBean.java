package com.youzi.service.api.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzw on 2019/5/7.
 * 描述:
 */
public class ThemeBean implements Serializable {


    /**
     * theme_img_id : 157
     * theme_upload_time : 2019-04-08 14:12:13.231329
     * theme_update_time : 2019-04-08 14:12:13.231329
     * home_theme_id : 157
     * theme_download_number : 0
     * theme_apk_url : http://47.107.99.30:8888\static1\img\图1554703892400陨石桌面.apk
     * theme_content : 2222222222222222222222222
     * species : 其他
     * theme_name : 22222222222222
     * id : 157
     * theme_video_url : http://47.107.99.30:8888\static1\img\图1554703890140录像5.mp4
     * detail : {"followNum":0,"videoUrl":"http://47.107.99.30:8888\\static1\\img\\图1554703890140录像5.mp4","collectNum":0,"themeDownloadNumber":0,"content":"2222222222222222222222222","imgList":[{"img_url":"http://47.107.99.30:8888\\static1\\img\\图15547038562471920X1080 (2).jpg","id":14,"f_id":157},{"img_url":"http://47.107.99.30:8888\\static1\\img\\图1554703858577qn.jpg","id":15,"f_id":157}]}
     * theme_upload_personnel : 123
     * theme_title : 2222222222222222
     */

    private String theme_img_id;
    private String theme_upload_time;
    private String theme_update_time;
    private String home_theme_id;
    private String theme_download_number;
    private String theme_apk_url;
    private String theme_content;
    private String species;
    private String share;
    private String theme_name;
    private String id;
    private String theme_video_url;
    private DetailBean detail;
    private String theme_upload_personnel;
    private String theme_title;


    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getTheme_img_id() {
        return theme_img_id;
    }

    public void setTheme_img_id(String theme_img_id) {
        this.theme_img_id = theme_img_id;
    }

    public String getTheme_upload_time() {
        return theme_upload_time;
    }

    public void setTheme_upload_time(String theme_upload_time) {
        this.theme_upload_time = theme_upload_time;
    }

    public String getTheme_update_time() {
        return theme_update_time;
    }

    public void setTheme_update_time(String theme_update_time) {
        this.theme_update_time = theme_update_time;
    }

    public String getHome_theme_id() {
        return home_theme_id;
    }

    public void setHome_theme_id(String home_theme_id) {
        this.home_theme_id = home_theme_id;
    }

    public String getTheme_download_number() {
        return theme_download_number;
    }

    public void setTheme_download_number(String theme_download_number) {
        this.theme_download_number = theme_download_number;
    }

    public String getTheme_apk_url() {
        return theme_apk_url;
    }

    public void setTheme_apk_url(String theme_apk_url) {
        this.theme_apk_url = theme_apk_url;
    }

    public String getTheme_content() {
        return theme_content;
    }

    public void setTheme_content(String theme_content) {
        this.theme_content = theme_content;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTheme_video_url() {
        return theme_video_url;
    }

    public void setTheme_video_url(String theme_video_url) {
        this.theme_video_url = theme_video_url;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public String getTheme_upload_personnel() {
        return theme_upload_personnel;
    }

    public void setTheme_upload_personnel(String theme_upload_personnel) {
        this.theme_upload_personnel = theme_upload_personnel;
    }

    public String getTheme_title() {
        return theme_title;
    }

    public void setTheme_title(String theme_title) {
        this.theme_title = theme_title;
    }

    public static class DetailBean implements Serializable {
        /**
         * followNum : 0
         * videoUrl : http://47.107.99.30:8888\static1\img\图1554703890140录像5.mp4
         * collectNum : 0
         * themeDownloadNumber : 0
         * content : 2222222222222222222222222
         * imgList : [{"img_url":"http://47.107.99.30:8888\\static1\\img\\图15547038562471920X1080 (2).jpg","id":14,"f_id":157},{"img_url":"http://47.107.99.30:8888\\static1\\img\\图1554703858577qn.jpg","id":15,"f_id":157}]
         */

        private String followNum;
        private String videoUrl;
        private String species;
        private String collectNum;
        private String themeDownloadNumber;
        private String content;
        private List<ImgListBean> imgList;


        public String getSpecies() {
            return species;
        }

        public void setSpecies(String species) {
            this.species = species;
        }

        public String getFollowNum() {
            return followNum;
        }

        public void setFollowNum(String followNum) {
            this.followNum = followNum;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(String collectNum) {
            this.collectNum = collectNum;
        }

        public String getThemeDownloadNumber() {
            return themeDownloadNumber;
        }

        public void setThemeDownloadNumber(String themeDownloadNumber) {
            this.themeDownloadNumber = themeDownloadNumber;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<DetailBean.ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<DetailBean.ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public static class ImgListBean implements Serializable {
            /**
             * img_url : http://47.107.99.30:8888\static1\img\图15547038562471920X1080 (2).jpg
             * id : 14
             * f_id : 157
             */

            private String img_url;
            private String id;
            private String f_id;

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getF_id() {
                return f_id;
            }

            public void setF_id(String f_id) {
                this.f_id = f_id;
            }
        }
    }
}
