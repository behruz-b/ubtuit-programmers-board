$ ->
  my.initAjax()

  Glob = window.Glob || {}

  apiUrl =
    addUser: '/createUser'
    getLanguage: '/getLang'
    addDirection: '/createDirection'


  defaultUserdata =
    firstName: ''
    lastName: ''
    login: ''
    password: ''
    photo: ''

  defaultLanguageData =
    name: ''
    logo: ''

  defaultDirectionData =
    name: ''

  Page =
    leaders: 'leaders'
    computerLanguages: 'computerLanguages'
    directions: 'directions'
    messages: 'messages'
    users: 'users'

  vm = ko.mapping.fromJS
    user: defaultUserdata
    language: defaultLanguageData
    direction: defaultDirectionData
    enableSubmitButton: yes
    page: Glob.page
    languageList: []

  vm.selectedPage = (page) ->
    if (page is Page.leaders)
      vm.page(Page.leaders)
    else if (page is Page.computerLanguages)
      vm.page(Page.computerLanguages)
    else if (page is Page.directions)
      vm.page(Page.directions)
    else if (page is Page.messages)
      vm.page(Page.messages)
    else
      vm.page(Page.users)

# Users Form methods coffee

  $contentFile = $('input[name=attachedFile]')
  $contentFile.change ->
    filePath = $(this).val()
    fileName = filePath.replace(/^.*[\\\/]/, '')

    reAllowedTypes = /^.+((\.jpg)|(\.jpeg)|(\.png))$/i
    if !reAllowedTypes.test(fileName)
      alert('Only PNG or JPG files are allowed.')
      return false

    $('#file-name').html fileName
    vm.enableSubmitButton yes

  formData = null
  $fileUploadForm = $('#file-upload-form')
  $fileUploadForm.fileupload
    dataType: 'text'
    autoUpload: no
    singleFileUploads: false
    replaceFileInput: true
    multipart: true
    add: (e, data) ->
      formData = data
    fail: (e, data) ->
      $('#progress').hide()
      handleError(data.jqXHR)
      vm.enableSubmitButton(yes)
    done: (e, data) ->
      $('#progress').hide()
      result = data.result
      if result is 'OK'
        toastr.success('Form has been successfully submitted!')
        ko.mapping.fromJS(defaultUserdata, {}, vm.user)
      else
        vm.enableSubmitButton(yes)
        toastr.error(result or 'Something went wrong! Please try again.')

# Computer Language Form coffee methods

  $contentLogo = $('input[name=attachedLogo]')
  $contentLogo.change ->
    filePath = $(this).val()
    fileName = filePath.replace(/^.*[\\\/]/, '')

    reAllowedTypes = /^.+((\.jpg)|(\.jpeg)|(\.png))$/i
    if !reAllowedTypes.test(fileName)
      alert('Only PNG or JPG files are allowed.')
      return false

    $('#logo-name').html fileName
    vm.enableSubmitButton yes

  formDataLanguage = null
  $logoUploadForm = $('#logo-upload-form')
  $logoUploadForm.fileupload
    dataType: 'text'
    autoUpload: no
    singleFileUploads: false
    replaceFileInput: true
    multipart: true
    add: (e, data) ->
      formDataLanguage = data
    fail: (e, data) ->
      handleError(data.jqXHR)
      vm.enableSubmitButton(yes)
    done: (e, data) ->
      result = data.result
      if result is 'OK'
        toastr.success('Form has been successfully submitted!')
        ko.mapping.fromJS(defaultLanguageData, {}, vm.language)
      else
        vm.enableSubmitButton(yes)
        toastr.error(result or 'Something went wrong! Please try again.')


  handleError = (error) ->
    if error.status is 500 or (error.status is 400 and error.responseText)
      toastr.error(error.responseText)
    else
      toastr.error('Something went wrong! Please try again.')

  vm.addLanguage = ->
    toastr.clear()
    if (!vm.language.name())
      toastr.error("Please enter a name")
      return no
    else if !formDataLanguage
      toastr.error("Please upload a logo the Computer Language")
      return no
    else if !formDataLanguage.files?.length
      toastr.error("Please upload a logo the Computer Language")
      return no
    if formDataLanguage
      vm.enableSubmitButton(no)
      formDataLanguage.submit()
    else
      $logoUploadForm.fileupload('send', {files: ''})
      return no

  getLanguage = ->
    $.ajax
      url:apiUrl.getLanguage
      type: 'GET'
    .fail handleError
    .done (response) ->
      for res in response
        res.logoName = "/assets/upload-files/" + res.logoName
      vm.languageList(response)

  getLanguage()

  vm.addDirection = ->
    toastr.clear()
    if (!vm.direction.name())
      toastr.error("Please enter a Direction Name")
      return no
    else
      data = ko.mapping.toJS(vm.direction.name())
      $.ajax
        url: apiUrl.addDirection
        type: 'POST'
        data: JSON.stringify(data)
        dataType: 'json'
        contentType: 'application/json'
      .fail handleError
      .done (response) ->
        toastr.success(response)

  vm.onSubmit = ->
    toastr.clear()
    if (!vm.user.firstName())
      toastr.error("Please enter first name")
      return no
    else if (!vm.user.lastName())
      toastr.error("Please enter last name")
      return no
    else if (!vm.user.login())
      toastr.error("Please enter login")
      return no
    else if (!vm.user.password())
      toastr.error("Please enter password")
      return no
    else if (!vm.user.password())
      toastr.error("Please enter password")
      return no
    else if !formData
      toastr.error('Please select file')
      return no
    else if !formData.files?.length
      toastr.error('Please select file')
      return no
    if formData
      vm.enableSubmitButton(no)
      formData.submit()
      $('#progress .bar').css('width', 0)
      $('#progress').show()
    else
      $fileUploadForm.fileupload('send', {files: ''})
      return no



  ko.applyBindings {vm}