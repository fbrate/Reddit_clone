package no.oslomet.channelpostsmicroservice.service;

import no.oslomet.channelpostsmicroservice.model.Channel;
import no.oslomet.channelpostsmicroservice.model.Post;
import no.oslomet.channelpostsmicroservice.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChannelService {
        @Autowired
        private ChannelRepository channelRepository;
        public List<Channel> getAllChannels(){return channelRepository.findAll();}
        public Channel getChannelById(String id){return channelRepository.findById(id).get();}
        public void saveChannel(Channel channel){channelRepository.save(channel);}
        public void deleteChannelById(String id){channelRepository.deleteById(id);}



}
