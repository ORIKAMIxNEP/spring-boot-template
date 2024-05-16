package jp.spring_boot_template.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jp.spring_boot_template.application.dto.record.AddInput;
import jp.spring_boot_template.application.dto.record.AddOutput;
import jp.spring_boot_template.application.dto.record.DeleteInput;
import jp.spring_boot_template.application.dto.record.DeleteOutput;
import jp.spring_boot_template.application.dto.record.FetchOutput;
import jp.spring_boot_template.application.dto.record.UpdateColumn1Input;
import jp.spring_boot_template.application.dto.record.UpdateColumn1Output;
import jp.spring_boot_template.application.dto.record.UpdateInput;
import jp.spring_boot_template.application.dto.record.UpdateOutput;
import jp.spring_boot_template.application.usecase.record.RecordUseCaseImpl;
import jp.spring_boot_template.presentation.controller.dto.AddRequest;
import jp.spring_boot_template.presentation.controller.dto.AddResponse;
import jp.spring_boot_template.presentation.controller.dto.BlueprintRequest;
import jp.spring_boot_template.presentation.controller.dto.BlueprintResponse;
import jp.spring_boot_template.presentation.controller.dto.DeleteRequest;
import jp.spring_boot_template.presentation.controller.dto.DeleteResponse;
import jp.spring_boot_template.presentation.controller.dto.FetchResponse;
import jp.spring_boot_template.presentation.controller.dto.UpdateColumn1Request;
import jp.spring_boot_template.presentation.controller.dto.UpdateColumn1Response;
import jp.spring_boot_template.presentation.controller.dto.UpdateRequest;
import jp.spring_boot_template.presentation.controller.dto.UpdateResponse;
import jp.spring_boot_template.presentation.error_handler.HttpClientErrorHandler;
import jp.spring_boot_template.presentation.error_handler.HttpClientErrorHandlerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
@RequiredArgsConstructor
public class RecordController {
  private final HttpClientErrorHandler httpClientErrorHandler;
  private final RecordUseCaseImpl recordUseCaseImpl;

  // レコード追加
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Operation(
      tags = {"record"},
      summary = "レコードを追加する",
      description = "カラム1、カラム2を受け取ってレコードを追加する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddResponse.class)))
      })
  public ResponseEntity<?> add(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final AddRequest addRequest,
      final BindingResult bindingResult) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    final AddOutput addOutput =
        recordUseCaseImpl.add(
            AddInput.builder().column1(addRequest.column1()).column2(addRequest.column2()).build());
    return ResponseEntity.ok(AddResponse.builder().success(addOutput.success()).build());
  }

  // レコード取得
  @GetMapping()
  @ResponseBody
  @Operation(
      tags = {"record"},
      summary = "レコードを取得する",
      description = "レコードを取得してレコードID、カラム1、カラム2を返す",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = FetchResponse.class)))
      })
  public ResponseEntity<?> fetch(@RequestHeader("X-CSRF-Token") String clientCsrfToken) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, null);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    final FetchOutput fetchOutput = recordUseCaseImpl.fetch();
    return ResponseEntity.ok(
        FetchResponse.builder()
            .column1(fetchOutput.column1())
            .column2(fetchOutput.column2())
            .build());
  }

  // レコード更新
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Operation(
      tags = {"record"},
      summary = "レコードを更新する",
      description = "レコードID、カラム1、カラム2を受け取ってレコードを更新する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateResponse.class)))
      })
  public ResponseEntity<?> update(
      @RequestHeader("X-CSRF-TOKEN") String clientCsrfToken,
      @RequestBody @Validated final UpdateRequest updateRequest,
      final BindingResult bindingResult) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    final UpdateOutput updateOutput =
        recordUseCaseImpl.update(
            UpdateInput.builder()
                .recordId(updateRequest.recordId())
                .column1(updateRequest.column1())
                .column2(updateRequest.column2())
                .build());
    return ResponseEntity.ok(UpdateResponse.builder().success(updateOutput.success()));
  }

  // レコードカラム1更新
  @PatchMapping(value = "/column1", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Operation(
      tags = {"record"},
      summary = "レコードカラム1を追加する",
      description = "レコードID、カラム1を受け取ってレコードを更新する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateColumn1Response.class)))
      })
  public ResponseEntity<?> updateColumn1(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final UpdateColumn1Request updateColumn1Request,
      final BindingResult bindingResult) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    final UpdateColumn1Output updateColumn1Output =
        recordUseCaseImpl.updateColumn1(
            UpdateColumn1Input.builder()
                .recordId(updateColumn1Request.recordId())
                .column1(updateColumn1Request.column1())
                .build());
    return ResponseEntity.ok(
        UpdateColumn1Response.builder().success(updateColumn1Output.success()));
  }

  // レコード削除
  @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Operation(
      tags = {"record"},
      summary = "レコードを削除する",
      description = "レコードIDを受け取ってレコードを削除する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DeleteResponse.class)))
      })
  public ResponseEntity<?> delete(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final DeleteRequest deleteRequest,
      final BindingResult bindingResult) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    final DeleteOutput deleteOutput =
        recordUseCaseImpl.delete(DeleteInput.builder().recordId(deleteRequest.recordId()).build());
    return ResponseEntity.ok(DeleteResponse.builder().success(deleteOutput.success()));
  }

  // レコード○○
  @PostMapping(value = "/blueprint", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @Operation(
      tags = {"record"},
      summary = "レコードを○○する",
      description = "レコードを○○する",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BlueprintResponse.class)))
      })
  public ResponseEntity<?> blueprint(
      @RequestHeader("X-CSRF-Token") String clientCsrfToken,
      @RequestBody @Validated final BlueprintRequest blueprintRequest,
      final BindingResult bindingResult) {
    final HttpClientErrorHandlerResponse httpClientErrorHandlerResponse =
        httpClientErrorHandler.handle(clientCsrfToken, bindingResult);
    if (httpClientErrorHandlerResponse.error()) {
      return httpClientErrorHandlerResponse.responseEntity();
    }
    return ResponseEntity.ok(
        BlueprintResponse.builder().recordId(blueprintRequest.recordId()).build());
  }
}
