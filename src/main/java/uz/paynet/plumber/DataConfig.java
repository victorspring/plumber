package uz.paynet.plumber;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.paynet.plumber.dto.HouseDto;
import uz.paynet.plumber.dto.PlumberDto;
import uz.paynet.plumber.entity.House;
import uz.paynet.plumber.entity.Plumber;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Bean
    public DataSource inMemoryDataSource() throws Exception {
        return EmbeddedPostgres.builder()
                .start()
                .getPostgresDatabase();
    }


}