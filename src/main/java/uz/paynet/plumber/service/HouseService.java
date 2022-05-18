package uz.paynet.plumber.service;

import uz.paynet.plumber.exception.HouseLimitExceedException;

public interface HouseService {
    void setPlumber(Long houseId, Long plumberId);
}
