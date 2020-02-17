package com.example.catalogue.catalogueservice.controller.manufacturer;

import com.example.catalogue.catalogueservice.controller.TestUtil;
import com.example.catalogue.catalogueservice.dto.ManufacturerDto;
import com.example.catalogue.catalogueservice.entity.Manufacturer;
import com.example.catalogue.catalogueservice.service.ManufacturerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ManufacturerControllerTest {

    private final static String URI = "/catalogue/manufacturer";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManufacturerService manufacturerServiceMock;

    @Test
    public void addManufacturer() throws Exception {
        Mockito.when(manufacturerServiceMock.addManufacturer(Mockito.any(Manufacturer.class)))
               .thenReturn(ManufacturerTestUtils.manufacturerServiceAnswer);

        MvcResult mvcResult = mockMvc.perform(post(URI)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(TestUtil.asJsonString(
                                                              ManufacturerTestUtils.manufacturerDtoToAdd)))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andReturn();

        ArgumentCaptor<Manufacturer> argumentCaptor = ArgumentCaptor.forClass(Manufacturer.class);
        verify(manufacturerServiceMock, times(1)).addManufacturer(argumentCaptor.capture());

        Manufacturer argument = argumentCaptor.getValue();
        assertNull(argument.getId());
        assertThat(argument.getName(), is(ManufacturerTestUtils.manufacturerToAdd.getName()));
        assertThat(argument.getEmail(), is(ManufacturerTestUtils.manufacturerToAdd.getEmail()));
        assertThat(argument.getFoundationYear(), is(ManufacturerTestUtils.manufacturerToAdd.getFoundationYear()));
        assertThat(argument.getAddress(), is(ManufacturerTestUtils.manufacturerToAdd.getAddress()));

        ManufacturerDto answer = TestUtil.asObject(mvcResult.getResponse().getContentAsString(), ManufacturerDto.class);
        assertThat(answer, is(ManufacturerTestUtils.manufacturerDtoAnswer));

    }

    @Test
    public void getManufacturers() throws Exception {

        Mockito.when(manufacturerServiceMock.getManufacturers(Mockito.any(Pageable.class)))
               .thenReturn(new PageImpl<>(Collections.singletonList(ManufacturerTestUtils.manufacturerServiceAnswer)));

        MvcResult mvcResult = mockMvc.perform(get(URI)
                                                      .param("page", "0")
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andExpect(jsonPath("$", hasSize(1)))
                                     .andReturn();

        Mockito.verify(manufacturerServiceMock, Mockito.times(1)).getManufacturers(Mockito.any(Pageable.class));

        List<ManufacturerDto> manufacturerDtos =
                Arrays.asList(TestUtil.asObject(mvcResult.getResponse().getContentAsString(), ManufacturerDto[].class));

        assertThat(manufacturerDtos.get(0), is(ManufacturerTestUtils.manufacturerDtoAnswer));

    }

    @Test
    public void updateManufacturer() throws Exception {
        Mockito.when(manufacturerServiceMock.updateManufacturer(Mockito.any(Manufacturer.class)))
               .thenReturn(ManufacturerTestUtils.manufacturerServiceAnswer);

        MvcResult mvcResult = mockMvc.perform(put(URI)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(TestUtil.asJsonString(
                                                              ManufacturerTestUtils.manufacturerDtoToUpdate)))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andReturn();

        ArgumentCaptor<Manufacturer> argumentCaptor = ArgumentCaptor.forClass(Manufacturer.class);
        verify(manufacturerServiceMock, times(1)).updateManufacturer(argumentCaptor.capture());

        Manufacturer argument = argumentCaptor.getValue();
        assertThat(argument.getId(), is(ManufacturerTestUtils.manufacturerToUpdate.getId()));
        assertThat(argument.getName(), is(ManufacturerTestUtils.manufacturerToUpdate.getName()));
        assertThat(argument.getEmail(), is(ManufacturerTestUtils.manufacturerToUpdate.getEmail()));
        assertThat(argument.getFoundationYear(), is(ManufacturerTestUtils.manufacturerToUpdate.getFoundationYear()));
        assertThat(argument.getAddress(), is(ManufacturerTestUtils.manufacturerToUpdate.getAddress()));

        ManufacturerDto answer = TestUtil.asObject(mvcResult.getResponse().getContentAsString(), ManufacturerDto.class);
        assertThat(answer, is(ManufacturerTestUtils.manufacturerDtoAnswer));
    }

    @Test
    public void deleteManufacturer() throws Exception {
        Mockito.doNothing().when(manufacturerServiceMock).deleteManufacturer(Mockito.any(Integer.class));

        mockMvc.perform(delete(URI + "/{id}", Mockito.anyInt())
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        verify(manufacturerServiceMock, times(1)).deleteManufacturer(Mockito.anyInt());
    }

    @Test
    public void getManufacturedById() throws Exception {
        Mockito.when(manufacturerServiceMock.getManufacturerById(Mockito.anyInt()))
               .thenReturn(ManufacturerTestUtils.manufacturerServiceAnswer);

        MvcResult mvcResult =
                mockMvc.perform(get(URI + "/{id}", ManufacturerTestUtils.manufacturerServiceAnswer.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(TestUtil.asJsonString(ManufacturerTestUtils.manufacturerToAdd)))
                       .andDo(print())
                       .andExpect(status().isOk())
                       .andReturn();

        ManufacturerDto answer = TestUtil.asObject(mvcResult.getResponse().getContentAsString(), ManufacturerDto.class);

        assertThat(answer, is(ManufacturerTestUtils.manufacturerDtoAnswer));

        Mockito.verify(manufacturerServiceMock, Mockito.times(1)).getManufacturerById(Mockito.anyInt());
    }
}