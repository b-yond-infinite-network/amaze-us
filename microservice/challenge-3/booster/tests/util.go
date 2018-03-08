package tests

import (
	"bytes"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"reflect"
)

const (
	POST = "POST"
	PUT  = "PUT"
	DEL  = "GET"
)

const JsonContentType = "application/json"

func HttpPost(url string, data []byte) error {

	buff := bytes.NewReader(data)
	resp, err := http.DefaultClient.Post(url, JsonContentType, buff)
	if err != nil {
		return err
	}

	if resp.StatusCode != http.StatusCreated {
		return fmt.Errorf("Error Posting to server(%s) response=%V", url, resp)
	}
	return nil
}

func HttpPut(url string, data []byte) error {

	buff := bytes.NewReader(data)

	req, err := http.NewRequest("PUT", url, buff)
	if err != nil {
		return err
	}
	req.Header.Set("Content-Type", JsonContentType)
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		return err
	}

	if resp.StatusCode != http.StatusOK {
		return fmt.Errorf("Error Putting to server(%s) response=%V", url, resp)
	}
	return nil
}

func HttpDel(url string, data []byte) error {

	buff := bytes.NewReader(data)

	req, err := http.NewRequest("DELETE", url, buff)
	if err != nil {
		return err
	}
	req.Header.Set("Content-Type", JsonContentType)
	resp, err := http.DefaultClient.Do(req)
	if err != nil {
		return err
	}

	if resp.StatusCode != http.StatusNoContent {
		return fmt.Errorf("Error Deleting to server(%s) response=%V", url, resp)
	}
	return nil
}

func HttpGet(url string) ([]byte, error) {

	resp, err := http.Get(url)
	if err != nil {
		return nil, err
	}
	if resp.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("Errror getting resp=%v", resp)
	}
	return ioutil.ReadAll(resp.Body)
}

func SendJson(url string, op string, data interface{}) error {

	dataBytes, err := json.Marshal(data)
	if err != nil {
		return err
	}

	switch op {
	case POST:
		return HttpPost(url, dataBytes)
	case PUT:
		return HttpPut(url, dataBytes)
	case DEL:
		return HttpDel(url, dataBytes)
	}
	return nil
}

func VerifyJsons(leftStruct, rightStruct interface{}, left, right []byte) bool {

	var err error

	err = json.Unmarshal(left, leftStruct)
	if err != nil {
		return false
	}

	err = json.Unmarshal(right, rightStruct)
	if err != nil {
		return false
	}

	return reflect.DeepEqual(leftStruct, rightStruct)
}

func MarshalStruct(dataStruct interface{}) []byte {
	resp, err := json.Marshal(dataStruct)
	if err != nil {
		return nil
	}
	return resp
}

func UnmarshalStruct(data []byte, dataStruct interface{}) error {
	err := json.Unmarshal(data, dataStruct)
	if err != nil {
		return err
	}
	return nil
}
