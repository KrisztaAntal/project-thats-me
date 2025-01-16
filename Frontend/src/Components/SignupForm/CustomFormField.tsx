import { CustomFormFieldProps } from '../../Types/MemberTypes';

function CustomFormField({ inputId, labelValue, inputType, inputValue, onChange, placeholder, onShowPasswordClick }: CustomFormFieldProps) {

  return (
    <div className="w-full justify-between flex relative gap-2">
      <label htmlFor={inputId}>{labelValue}</label>
      <input className='placeholder:pl-1' id={inputId} type={inputType} value={inputValue} onChange={onChange} placeholder={placeholder} required />
      {labelValue === "Password" &&
        <img className='absolute right-0 top-1/2 transform -translate-y-1/2 cursor-pointer'
          src={inputType === "password" ? "./lock-closed.svg" : "./lock-open.svg"}
          alt={inputType === "password" ? 'show password' : 'hide password'}
          title={inputType === "password" ? 'show' : 'hide'}
          onClick={onShowPasswordClick}></img>}
    </div>
  )
}

export default CustomFormField;