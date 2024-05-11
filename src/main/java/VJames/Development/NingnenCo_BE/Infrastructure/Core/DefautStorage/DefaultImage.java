package VJames.Development.NingnenCo_BE.Infrastructure.Core.DefautStorage;

import VJames.Development.NingnenCo_BE.Application.DefautStorage.IDefaultImage;

public class DefaultImage implements IDefaultImage {
    private final String imageUrl;

    public DefaultImage() {
        this.imageUrl = "https://res.cloudinary.com/dy1uuo6ql/image/upload/v1708924368/ipghhsijubgdawyxo99v.jpg";
    }

    @Override
    public String getImage() {
        return imageUrl;
    }
}
