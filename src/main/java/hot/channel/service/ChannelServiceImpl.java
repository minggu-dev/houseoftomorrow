package hot.channel.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import hot.aws.S3Manager;
import hot.member.domain.Channel;
import hot.member.repository.ChannelRepository;
import hot.member.repository.ConstructorRepository;

@Service
public class ChannelServiceImpl implements ChannelService {
	@Autowired
	private ChannelRepository channelRepository;
	@Autowired
	private ConstructorRepository constructorRepository;
	@Autowired
	private S3Manager s3Manager;
	/**
	 * 채널들록
	 */
	@Override
	@Transactional
	public void insertChannel(Channel channel, MultipartFile chImg) {
		try {
			if (chImg.isEmpty())throw new RuntimeException("파일이 없습니다.");
			String fileName = s3Manager.saveUploadedFiles(chImg);
			channel.setChImg(fileName);
			constructorRepository.save(channel.getConstructor());
			channelRepository.save(channel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
