package jp.spring_boot_template.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import jp.spring_boot_template.application.dto.AddInput;
import jp.spring_boot_template.application.dto.AddOutput;
import jp.spring_boot_template.application.dto.DeleteInput;
import jp.spring_boot_template.application.dto.DeleteOutput;
import jp.spring_boot_template.application.dto.FetchInput;
import jp.spring_boot_template.application.dto.FetchOutput;
import jp.spring_boot_template.application.dto.UpdateColumn1Input;
import jp.spring_boot_template.application.dto.UpdateColumn1Output;
import jp.spring_boot_template.application.dto.UpdateInput;
import jp.spring_boot_template.application.dto.UpdateOutput;
import jp.spring_boot_template.domain.model.entity.Records;
import jp.spring_boot_template.infrastructure.repository.RecordsRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
public class RecordUseCaseImplTest {
  @Mock private final RecordsRepositoryImpl recordsRepositoryImplMock;

  @InjectMocks private final RecordUseCaseImpl recordUseCaseImpl;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void addTest() {
    assertThat(recordUseCaseImpl.add(AddInput.builder().column1((short) 0).column2("a").build()))
        .isEqualTo(AddOutput.builder().success(true).build());
  }

  @Test
  public void fetchTest() {
    when(recordsRepositoryImplMock.fetch(1))
        .thenReturn(Records.builder().recordId(1).column1((short) 0).column2("a").build());
    when(recordsRepositoryImplMock.fetch(2)).thenReturn(null);

    assertThat(recordUseCaseImpl.fetch(FetchInput.builder().recordId(1).build()))
        .isEqualTo(FetchOutput.builder().success(true).column1((short) 0).column2("a").build());
    assertThat(recordUseCaseImpl.fetch(FetchInput.builder().recordId(2).build()))
        .isEqualTo(FetchOutput.builder().success(false).column1(null).column2(null).build());
  }

  @Test
  public void updateTest() {
    when(recordsRepositoryImplMock.fetch(1))
        .thenReturn(Records.builder().recordId(1).column1((short) 0).column2("a").build());
    when(recordsRepositoryImplMock.fetch(2)).thenReturn(null);

    assertThat(
            recordUseCaseImpl.update(
                UpdateInput.builder().recordId(1).column1((short) 0).column2("a").build()))
        .isEqualTo(UpdateOutput.builder().success(true).build());
    assertThat(
            recordUseCaseImpl.update(
                UpdateInput.builder().recordId(2).column1((short) 0).column2("a").build()))
        .isEqualTo(UpdateOutput.builder().success(false).build());
  }

  @Test
  public void updateColumn1Test() {
    when(recordsRepositoryImplMock.fetch(1))
        .thenReturn(Records.builder().recordId(1).column1((short) 0).column2("a").build());
    when(recordsRepositoryImplMock.fetch(2)).thenReturn(null);

    assertThat(
            recordUseCaseImpl.updateColumn1(
                UpdateColumn1Input.builder().recordId(1).column1((short) 0).build()))
        .isEqualTo(UpdateColumn1Output.builder().success(true).build());
    assertThat(
            recordUseCaseImpl.updateColumn1(
                UpdateColumn1Input.builder().recordId(2).column1((short) 0).build()))
        .isEqualTo(UpdateColumn1Output.builder().success(false).build());
  }

  @Test
  public void deleteTest() {
    when(recordsRepositoryImplMock.fetch(1))
        .thenReturn(Records.builder().recordId(1).column1((short) 0).column2("a").build());
    when(recordsRepositoryImplMock.fetch(2)).thenReturn(null);

    assertThat(recordUseCaseImpl.delete(DeleteInput.builder().recordId(1).build()))
        .isEqualTo(DeleteOutput.builder().success(true).build());
    assertThat(recordUseCaseImpl.delete(DeleteInput.builder().recordId(2).build()))
        .isEqualTo(DeleteOutput.builder().success(false).build());
  }
}
