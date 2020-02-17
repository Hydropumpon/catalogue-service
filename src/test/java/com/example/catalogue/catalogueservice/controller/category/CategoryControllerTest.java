package com.example.catalogue.catalogueservice.controller.category;

import com.example.catalogue.catalogueservice.controller.TestUtil;
import com.example.catalogue.catalogueservice.dto.CategoryDto;
import com.example.catalogue.catalogueservice.entity.Category;
import com.example.catalogue.catalogueservice.service.CategoryService;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    private final static String URI = "/catalogue/category";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryServiceMock;

    @Test
    public void getCategories() throws Exception {

        Mockito.when(categoryServiceMock.getCategories(Mockito.any(Pageable.class)))
               .thenReturn(new PageImpl<>(Collections.singletonList(CategoryTestUtils.categoryServiceAnswer)));

        mockMvc.perform(get(URI)
                                .param("page", "0")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(
                       jsonPath("$[0].name", is(CategoryTestUtils.categoryDtoAnswer.getName())))
               .andExpect(jsonPath("$[0].id", is(CategoryTestUtils.categoryDtoAnswer.getId())));
    }

    //TODO Спросить как лучше делать - через парсинг Json или десеарилизацию
    @Test
    public void getCategory() throws Exception {
        Mockito.when(categoryServiceMock.getCategoryById(CategoryTestUtils.categoryServiceAnswer.getId()))
               .thenReturn(CategoryTestUtils.categoryServiceAnswer);

        MvcResult mvcResult = mockMvc.perform(get(URI + "/{id}", CategoryTestUtils.categoryServiceAnswer.getId())
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andExpect(jsonPath("$.name", is(CategoryTestUtils.categoryDtoAnswer.getName())))
                                     .andExpect(jsonPath("$.id", is(CategoryTestUtils.categoryDtoAnswer.getId())))
                                     .andReturn();

        CategoryDto answer = TestUtil.asObject(mvcResult.getResponse().getContentAsString(), CategoryDto.class);

        assertThat(answer, is(CategoryTestUtils.categoryDtoAnswer));

    }

    @Test
    public void addCategory() throws Exception {
        Mockito.when(categoryServiceMock.addCategory(Mockito.any(Category.class)))
               .thenReturn(CategoryTestUtils.categoryServiceAnswer);

        MvcResult mvcResult = mockMvc.perform(post(URI)
                                                      .content(
                                                              TestUtil.asJsonString(CategoryTestUtils.categoryDtoToAdd))
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andExpect(jsonPath("$.name", is(CategoryTestUtils.categoryDtoAnswer.getName())))
                                     .andExpect(jsonPath("$.id", is(CategoryTestUtils.categoryDtoAnswer.getId())))
                                     .andReturn();

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryServiceMock, times(1)).addCategory(argumentCaptor.capture());

        Category argument = argumentCaptor.getValue();
        assertNull(argument.getId());
        assertThat(argument.getName(), is(CategoryTestUtils.categoryDtoToAdd.getName()));

        CategoryDto answer = TestUtil.asObject(mvcResult.getResponse().getContentAsString(), CategoryDto.class);

        assertThat(answer, is(CategoryTestUtils.categoryDtoAnswer));
    }

    @Test
    public void updateCategory() throws Exception {
        Mockito.when(categoryServiceMock.updateCategory(Mockito.any(Category.class)))
               .thenReturn(CategoryTestUtils.categoryServiceAnswer);

        MvcResult mvcResult = mockMvc.perform(put(URI)
                                                      .content(TestUtil.asJsonString(
                                                              CategoryTestUtils.categoryDtoToUpdate))
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andDo(print())
                                     .andExpect(status().isOk())
                                     .andExpect(jsonPath("$.name", is(CategoryTestUtils.categoryDtoAnswer.getName())))
                                     .andExpect(jsonPath("$.id", is(CategoryTestUtils.categoryDtoAnswer.getId())))
                                     .andReturn();

        ArgumentCaptor<Category> argumentCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryServiceMock, times(1)).updateCategory(argumentCaptor.capture());

        Category argument = argumentCaptor.getValue();
        assertThat(argument.getId(), is(CategoryTestUtils.categoryDtoToUpdate.getId()));
        assertThat(argument.getName(), is(CategoryTestUtils.categoryDtoToUpdate.getName()));

        CategoryDto answer = TestUtil.asObject(mvcResult.getResponse().getContentAsString(), CategoryDto.class);

        assertThat(answer, is(CategoryTestUtils.categoryDtoAnswer));
    }

    @Test
    public void deleteCategory() throws Exception {
        Mockito.doNothing().when(categoryServiceMock).deleteCategory(Mockito.any(Integer.class));

        mockMvc.perform(delete(URI + "/{id}", Mockito.anyInt())
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());

        verify(categoryServiceMock, times(1)).deleteCategory(Mockito.anyInt());
    }
}