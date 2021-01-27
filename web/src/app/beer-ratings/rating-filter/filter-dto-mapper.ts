import {FormGroup} from '@angular/forms';
import {FilterDto} from './filter-dto';
import {RatingFilterFormFields} from './rating-filter-form-fields';
import {Injectable} from '@angular/core';

@Injectable()
export class FilterDtoMapper {
  public static toDto(formGroup: FormGroup) {
    let filterDto = new FilterDto();
    filterDto.userIdentifier = formGroup.get(RatingFilterFormFields.USER_IDENTIFIER).value;
    filterDto.beerName = formGroup.get(RatingFilterFormFields.BEER_NAME).value;
    return filterDto;
  }
}
