package org.faketri.infrastructure.persistence.repository;

import org.faketri.domain.entity.driver.Profile;
import org.faketri.domain.entity.mapper.ProfileMapper;
import org.faketri.domain.repository.ProfileRepository;
import org.faketri.infrastructure.persistence.entity.EVerificationStatus;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaDriverRepositoryImpl implements ProfileRepository {

    private final DriverRepository driverRepository;
    private final ProfileMapper profileMapper;

    public JpaDriverRepositoryImpl(DriverRepository driverRepository, ProfileMapper profileMapper) {
        this.driverRepository = driverRepository;
        this.profileMapper = profileMapper;
    }


    @Override
    public Optional<Profile> get(UUID uid) {
        return driverRepository.findById(uid).map(profileMapper::toDomain);
    }

    @Override
    public Boolean verificationApprove(UUID uid){
        return driverRepository.updateVerification(EVerificationStatus.APPROVE, uid) > 0;
    }

    @Override
    public Boolean verificationFailure(UUID uid){
        return driverRepository.updateVerification(EVerificationStatus.FAILED, uid) > 0;
    }

    @Override
    public Profile save(Profile profile){
        var e = profileMapper.toEntity(profile);
        return profileMapper.toDomain(driverRepository.save(e));
    }

}
