package com.example.filmlibrary.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

//
//@Slf4j
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class DirectorServiceTest
//        extends GenericTest<Director, DirectorDTO> {
//
//
//    public DirectorServiceTest() {
//        super();
//        repository = Mockito.mock(DirectorRepository.class);
//        mapper = Mockito.mock(DirectorMapper.class);
//        service = new DirectorService((DirectorRepository) repository, (DirectorMapper) mapper);
//    }
//
//    @Test
//    @Order(1)
//    @Override
//    protected void getAll() {
//        Mockito.when(repository.findAll()).thenReturn(DirectorTestData.DIRECTOR_LIST);
//        Mockito.when(mapper.toDTOs(DirectorTestData.DIRECTOR_LIST)).thenReturn(DirectorTestData.DIRECTOR_DTO_LIST);
//
//        List<DirectorDTO> directorDTOS = service.listAll();
//        log.info("Testing getAll(): {}", directorDTOS);
//        assertEquals(DirectorTestData.DIRECTOR_LIST.size(), directorDTOS.size());
//    }
//
//
//
//    @Override
//    protected void getAllNotDeleted() {
//
//    }
//
//    @Order(2)
//    @Test
//    @Override
//    public void getOne() { //TODO://Negative test to do
//        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(DirectorTestData.DIRECTOR_1));
//        Mockito.when(mapper.toDTO(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_DTO_1);
//
//        DirectorDTO directorDTO = service.getOne(1L);
//        log.info("Testing getOne(): {}", directorDTO);
//        assertEquals(DirectorTestData.DIRECTOR_DTO_1, directorDTO);
//    }
//
//    @Order(3)
//    @Test
//    @Override
//    protected void create() {
//        Mockito.when(mapper.toEntity(DirectorTestData.DIRECTOR_DTO_1)).thenReturn(DirectorTestData.DIRECTOR_1);
//        Mockito.when(mapper.toDTO(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_DTO_1);
//        Mockito.when(repository.save(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_1);
//
//        DirectorDTO directorDTO = service.create(DirectorTestData.DIRECTOR_DTO_1);
//        log.info("Testing create(): {}", directorDTO);
//    }
//
//    @Order(4)
//    @Test
//    @Override
//    protected void update() {
//        Mockito.when(mapper.toEntity(DirectorTestData.DIRECTOR_DTO_1)).thenReturn(DirectorTestData.DIRECTOR_1);
//        Mockito.when(mapper.toDTO(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_DTO_1);
//        Mockito.when(repository.save(DirectorTestData.DIRECTOR_1)).thenReturn(DirectorTestData.DIRECTOR_1);
//
//        DirectorDTO directorDTO = service.update(DirectorTestData.DIRECTOR_DTO_1);
//        log.info("Testing create(): {}", directorDTO);
//    }


//}


