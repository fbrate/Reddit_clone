package no.oslomet.authenticationfrontendmicroservice.service;

import no.oslomet.authenticationfrontendmicroservice.model.Channel;
import no.oslomet.authenticationfrontendmicroservice.model.ForumUser;
import no.oslomet.authenticationfrontendmicroservice.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelService {
         String BASE_URL = "http://localhost:9091/channels";
         private RestTemplate restTemplate = new RestTemplate();

        public List<Channel> getAllChannels() {
                return Arrays.stream(
                        restTemplate.getForObject(BASE_URL, Channel[].class)
                ).collect(Collectors.toList());
        }

        public Channel getChannelById(String id){
                return restTemplate.getForObject(BASE_URL+"/"+id, Channel.class);
        }
        public List<Channel> getChannelInUser(long userid)
        {
                return Arrays.stream(
                        restTemplate.getForObject(BASE_URL+"/chaninuser/"+userid, Channel[].class)
                ).collect(Collectors.toList());
        }



        public List<Channel> searchListByString(String search)
        {
                List<Channel> channelsToReturn = new ArrayList<>();
                List<Channel> channels = getAllChannels();
                for(Channel c : channels)
                {
                        if(c.getTitle().toLowerCase().startsWith(search.toLowerCase())) {
                                channelsToReturn.add(c);
                        }
                }
                return channelsToReturn;
        }
        public void saveChannel(Channel channel){
                restTemplate.postForObject(BASE_URL, channel, Channel.class);
        }
}