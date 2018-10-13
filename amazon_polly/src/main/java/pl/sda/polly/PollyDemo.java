package pl.sda.polly;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.*;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;



public class PollyDemo {

    private final String VOICE_NAME = "Ewa";

    private final AmazonPollyClient polly;
    private final Voice voice;
    private static final String SAMPLE = "Witam wszystkich kursantów S.D.A. na zajęciach z serwerów. To tylko namiastka tego co potrafią chmury.";
    private static String BREATH_TAGS = "<speak><amazon:auto-breaths frequency=\"low\" volume=\"soft\" duration=\"x-short\">%s</amazon:auto-breaths></speak>";
            public PollyDemo(Region region) {
        // create an Amazon Polly client in a specific region
        polly = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(),
                new ClientConfiguration());
        polly.setRegion(region);
        // Create describe voices request.
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

        // Synchronously ask Amazon Polly to describe available TTS voices.
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
        List<Voice> voices = describeVoicesResult.getVoices();
        voice = getVoice(voices);

    }

    private Voice getVoice(List<Voice> voices) {
        for (Voice voice1 : voices) {
            if(voice1.getId().equals(VOICE_NAME)){
                return voice1;
            }
        }
        return voices.get(0);
    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest().withText(text).withTextType(TextType.Ssml).withVoiceId(voice.getId())
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

    public static void main(String args[]) throws Exception {
        //create the test class
        PollyDemo helloWorld = new PollyDemo(Region.getRegion(Regions.US_EAST_1));
        //get the audio stream
        InputStream speechStream = helloWorld.synthesize(String.format(BREATH_TAGS, SAMPLE), OutputFormat.Mp3);

        //create an MP3 player
        AdvancedPlayer player = new AdvancedPlayer(speechStream,
                javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

        player.setPlayBackListener(new PlaybackListener() {
            @Override
            public void playbackStarted(PlaybackEvent evt) {
                System.out.println("Playback started");
                System.out.println(SAMPLE);
            }

            @Override
            public void playbackFinished(PlaybackEvent evt) {
                System.out.println("Playback finished");
            }
        });


        // play it!
        player.play();

    }
}