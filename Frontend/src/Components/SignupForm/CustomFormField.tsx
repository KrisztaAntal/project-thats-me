import reactLogo from '../../assets/react.svg';
import { CustomFormFieldProps } from '../../Types/MemberTypes';

function CustomFormField({ inputId, labelValue, inputType, inputValue, onChange, placeholder, onShowPasswordClick }: CustomFormFieldProps) {

  return (
    <div className="w-full justify-between flex">
      <label htmlFor={inputId}>{labelValue}</label>
      <input id={inputId} type={inputType} value={inputValue} onChange={onChange} placeholder={placeholder} required />
      {labelValue === "Password" && <img src={reactLogo} alt="showPassword" onClick={onShowPasswordClick}></img>}
    </div>
  )
}

export default CustomFormField;